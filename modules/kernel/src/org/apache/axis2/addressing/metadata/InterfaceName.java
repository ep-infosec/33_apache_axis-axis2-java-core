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

package org.apache.axis2.addressing.metadata;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.AddressingConstants;
import org.apache.axis2.addressing.AddressingConstants.Final;
import org.apache.axis2.addressing.AddressingConstants.Submission;

import javax.xml.namespace.QName;

public class InterfaceName {
    public static final QName subQName = new QName(Submission.WSA_NAMESPACE, Submission.WSA_INTERFACE_NAME, AddressingConstants.WSA_DEFAULT_PREFIX);
    public static final QName wsamQName = new QName(Final.WSAM_NAMESPACE, Final.WSA_INTERFACE_NAME, Final.WSA_DEFAULT_METADATA_PREFIX);
    public static final QName wsawQName = new QName(Final.WSAW_NAMESPACE, Final.WSA_INTERFACE_NAME, Final.WSA_ORIGINAL_METADATA_PREFIX);
    
    /**
     * Field name
     */
    private QName name;

    /**
     * 
     */
    public InterfaceName() {
    }

    /**
     * @param name
     */
    public InterfaceName(QName name) {
        this.name = name;
    }

    /**
     * Method getName
     */
    public QName getName() {
        return name;
    }

    /**
     * Method setName
     *
     * @param name
     */
    public void setName(QName name) {
        this.name = name;
    }
    
    /**
     * Convenience method to convert objects of this type to an {@link OMElement} so that it
     * can be added to an {@link org.apache.axis2.addressing.EndpointReference}
     * 
     * <p>Use:</p>
     * <p>
     * OMElement omElement =
     * serviceName.toOM(new QName("http://schemas.xmlsoap.org/ws/2004/08/addressing", "PortType", "wsa"));
     * </p>
     * <p>or</p>
     * <p>
     * OMElement omElement =
     * serviceName.toOM(new QName("http://www.w3.org/2007/05/addressing/metadata", "InterfaceName", "wsam"));
     * </p>
     * <p>
     * the difference being whether the EndpointReference is meant to represent a 2004/08
     * (Submission) or 2005/08 (Final) EndpointReference, respectively.
     * </p>
     * 
     * @param qname the <code>QName</code> that carries the namespace of the metadata element.
     * @param fac TODO
     * @return an OMElement that can be added to the metadata of an EndpointReference.
     */
    public OMElement toOM(OMFactory fac, QName qname) throws AxisFault {
        String prefix = qname.getPrefix();
        if (prefix == null) {
            throw new AxisFault("The prefix cannot be null.");
        }
        
        String localName = qname.getLocalPart();
        if (!Final.WSA_INTERFACE_NAME.equals(localName) && !Submission.WSA_INTERFACE_NAME.equals(localName)) {
            throw new AxisFault("The local name must be 'InterfaceName' or 'PortType'.");
        }
        
        String namespace = qname.getNamespaceURI();
        if (namespace == null) {
            throw new AxisFault("The namespace canot be null.");
        }
            
        OMNamespace metadataNs = fac.createOMNamespace(namespace, prefix);
        OMElement element = fac.createOMElement(localName, metadataNs);
        element.setText(name);

        return element;
    }
    
    /**
     * Convenience method to extract metadata from an element.
     * 
     * <p>
     * &lt;wsam:InterfaceName xmlns:wsam="http://www.w3.org/2007/05/addressing/metadata"&gt;...&lt;/wsam:ServiceName&gt;
     * </p>
     * <p>or</p>
     * <p>
     * &lt;wsa:PortType xmlns:wsa="http://schemas.xmlsoap.org/ws/2004/08/addressing"&gt;...&lt;/wsa:ServiceName&gt;
     * </p>
     * 
     * @param omElement the <code>OMElement</code> that holds the metadata.
     */
    public void fromOM(OMElement omElement) throws AxisFault {
        QName qname = omElement.getQName();
        
        if (!wsamQName.equals(qname) && !wsawQName.equals(qname) && !subQName.equals(qname)) {
            throw new AxisFault("Unrecognized element.");
        }

        name = omElement.getTextAsQName();
    }
    
    /**
     * Static method to test whether an <code>OMElement</code> is recognized
     * as a ServiceName element. If this method returns <code>true</code> then
     * {@link #fromOM(OMElement)} is guaranteed not to fail.
     * 
     * @param omElement the <code>OMElement</code> to test.
     * @return <code>true</code> if the element is a ServiceName element,
     * <code>false</code> otherwise.
     */
    public static boolean isInterfaceNameElement(OMElement omElement) {
        boolean result = false;
        QName qname = omElement.getQName();
        
        if (wsamQName.equals(qname) || wsawQName.equals(qname) || subQName.equals(qname))
            result = true;
        
        return result;
    }
}
