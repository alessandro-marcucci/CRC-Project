/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2000 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "SOAP" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 2000, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

package samples.lemurlabs;

import java.io.*;
import java.util.*;
import java.net.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.rpc.*;
import org.apache.soap.transport.http.SOAPHTTPConnection;

/**
 * Client to speak to lemurlabs random fortune cookie service
 * http://www.lemurlabs.com/projects/soap/fortune/
 *
 * @author Wouter Cloetens (wouter@mind.be)
 */
public class Fortune {
    public static final String DEFAULT_SERVICE_URL =
        "http://www.lemurlabs.com/rpcrouter";

    public static void main(String[] args) throws Exception {
        String serviceURL = null;
        String proxyHost = null;
        int proxyPort = -1;
        boolean getList = false;
        String dictionary = null;

        // parse command line arguments
        boolean goodUsage = true;
        for (int i = 0; i < args.length; i++) {
            if ("-p".equalsIgnoreCase(args[i])) {
                goodUsage = false;
                if (args.length > i) {
                    i++;
                    int pos = args[i].indexOf(':');
                    if (pos != -1) {
                        try {
                            proxyPort =
                                Integer.parseInt(args[i].substring(pos + 1));
                            proxyHost = args[i].substring(0, pos);
                            goodUsage = true;
                        } catch(NumberFormatException nfe) {
                        }
                    }
                }
            } else if ("-u".equalsIgnoreCase(args[i])) {
                goodUsage = false;
                if (args.length > i && serviceURL == null) {
                    i++;
                    serviceURL = args[i];
                    goodUsage = true;
                } else
                    break;
            } else if ("-l".equalsIgnoreCase(args[i])) {
                getList = true;
            } else if (dictionary == null) {
                if (args[i].charAt(0) == '-') {
                    goodUsage = false;
                    break;
                }
                dictionary = args[i];
            } else
                break;
        }

        if (!goodUsage) {
            System.err.println("Usage: java samples.lemurlabs.Fortune " +
                               "[-u service-URL]\n" +
                               "       [-p <HTTP proxy hostname:port>] " +
                               "[-l | dictionary]\n" +
                               "-l: list dictionaries");
            System.exit(1);
        }

        if (serviceURL == null)
            serviceURL = DEFAULT_SERVICE_URL;
        URL url = new URL(serviceURL);

        // create the transport and set parameters
        SOAPHTTPConnection st = new SOAPHTTPConnection();
        if (proxyHost != null) {
            st.setProxyHost(proxyHost);
            st.setProxyPort(proxyPort);
        }

        // build the call.
        Call call = new Call();
        call.setSOAPTransport(st);
        call.setTargetObjectURI("urn:lemurlabs-Fortune");
        if (getList)
            call.setMethodName("getDictionaryNameList");
        else if (dictionary != null) {
            call.setMethodName("getFortuneByDictionary");
            Vector params = new Vector();
            params.addElement(new Parameter("dictionary", String.class,
                                            dictionary, null));
            call.setParams(params);
        } else
            call.setMethodName("getAnyFortune");
        call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);

        // invoke it
        System.err.println("Invoking fortune service at: ");
        System.err.println("\t" + serviceURL);
        Response resp;
        try {
            // resp = call.invoke(url, null);

            /**
             * Elaborate work-around. lemurlabs does not like the "charset"
             * modifier on the Content-Type.
             * The work-around mostly consists of a manual implementation
             * of Call.invoke(), with instead of passing the request envelope
             * to the SOAPTransport, manually marshalling it and setting the
             * result as the root part of the request SOAPContext, with a
             * provided Content-Type "text/xml" instead of the default
             * "text/xml; charset=utf-8".
             */
            SOAPMappingRegistry smr = call.getSOAPMappingRegistry();
            SOAPContext reqCtx = call.getSOAPContext();
            DocumentBuilder xdb = XMLParserUtils.getXMLDocBuilder();

            Envelope callEnv = call.buildEnvelope();
            StringWriter payloadSW = new StringWriter();
            callEnv.marshall(payloadSW, smr, reqCtx);
            reqCtx.setRootPart(payloadSW.toString(),
                               Constants.HEADERVAL_CONTENT_TYPE);

            st.send(url, "", null, null, smr, reqCtx);

            SOAPContext respCtx = st.getResponseSOAPContext();
            String payloadStr = Call.getEnvelopeString(st);
            Document respDoc =
              xdb.parse(new InputSource(new StringReader(payloadStr)));
            Element payload = null;
            if (respDoc != null)
            {
                payload = respDoc.getDocumentElement();
            }
            else
            {
                throw new SOAPException (Constants.FAULT_CODE_CLIENT,
                    "Parsing error, response was:\n" + payloadStr);
            }
            Envelope respEnv = Envelope.unmarshall(payload, respCtx);
            resp = Response.extractFromEnvelope(respEnv, smr, respCtx);
            /**
             * End of work-around.
             */
        } catch(SOAPException e) {
            System.err.println("Caught SOAPException (" +
                               e.getFaultCode () + "): " +
                               e.getMessage ());
            return;
        }

        // check response
        if (!resp.generatedFault()) {
            Parameter ret = resp.getReturnValue();
            Object value = ret.getValue();

            if (getList) {
                System.out.println("Dictionary list:");
                String[] dictionaries = (String[])value;
                for (int i = 0; i < dictionaries.length; i++)
                    System.out.println(dictionaries[i]);
            } else
                System.out.println((String)value);
        } else {
            Fault fault = resp.getFault();
            System.err.println("Generated fault: ");
            System.out.println("  Fault Code   = " + fault.getFaultCode());
            System.out.println("  Fault String = " + fault.getFaultString());
        }
    }
}
