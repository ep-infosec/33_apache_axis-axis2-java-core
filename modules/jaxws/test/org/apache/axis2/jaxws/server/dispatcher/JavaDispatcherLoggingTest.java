/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *      
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.axis2.jaxws.server.dispatcher;

import org.apache.axis2.jaxws.core.MessageContext;
import org.apache.axis2.jaxws.server.EndpointCallback;

import junit.framework.TestCase;

/**
 * Verify logging in the JavaDispatcher.  This does not verify any of the 
 * dispatching logic.
 */
public class JavaDispatcherLoggingTest extends TestCase {

    /**
     * Verify the logging of the context classloader before and after invocation
     */
    public void testLogContextClassloader() {
        TestDispatcherLogging testDispatcher = new TestDispatcherLogging(null, null);
        assertTrue(testDispatcher.logContextClassLoader("Before").startsWith("Current ThreadContextClassLoader: Before: "));
        assertTrue(testDispatcher.logContextClassLoader(null).startsWith("Current ThreadContextClassLoader: "));
    }

}

class TestDispatcherLogging extends JavaDispatcher {
    TestDispatcherLogging(Class impl, Object serviceInstance) {
        super(impl, serviceInstance);
    }
    @Override
    protected MessageContext createFaultResponse(MessageContext request,
            Throwable fault) {
        return null;
    }

    @Override
    protected MessageContext createResponse(MessageContext request,
            Object[] input, Object output) {
        return null;
    }

    @Override
    public MessageContext invoke(MessageContext request) throws Exception {
        return null;
    }

    @Override
    public void invokeAsync(MessageContext request, EndpointCallback callback) {
        
    }

    @Override
    public void invokeOneWay(MessageContext request) {
        
    }
    
}
