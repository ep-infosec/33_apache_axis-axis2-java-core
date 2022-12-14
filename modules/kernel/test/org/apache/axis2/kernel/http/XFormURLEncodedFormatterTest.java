/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.axis2.kernel.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.OutputStream;
import java.io.StringWriter;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMOutputFormat;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPFactory;
import org.apache.axis2.context.MessageContext;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.Test;

public class XFormURLEncodedFormatterTest {
    @Test
    public void test() throws Exception {
        SOAPFactory factory = OMAbstractFactory.getSOAP11Factory();
        SOAPEnvelope envelope = factory.createDefaultSOAPMessage().getSOAPEnvelope();
        OMElement request = factory.createOMElement("request", null, envelope.getBody());
        factory.createOMElement("param1", null, request).setText("value1");
        factory.createOMElement("param2", null, request).setText("value2");
        MessageContext messageContext = new MessageContext();
        messageContext.setEnvelope(envelope);
        StringWriter sw = new StringWriter();
        OutputStream out = new WriterOutputStream(sw, "utf-8");
        new XFormURLEncodedFormatter().writeTo(messageContext, new OMOutputFormat(), out, true);
        out.close();
        assertThat(sw.toString()).isEqualTo("param1=value1&param2=value2");
    }
}
