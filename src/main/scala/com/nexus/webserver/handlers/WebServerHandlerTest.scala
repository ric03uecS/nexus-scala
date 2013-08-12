/*
 * Copyright 2013 TeamNexus
 *
 * TeamNexus Licenses this file to you under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 *    http://opensource.org/licenses/mit-license.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License
 */

package com.nexus.webserver.handlers

import com.nexus.webserver.{WebServerResponse, WebServerRequest, TWebServerHandler}
import io.netty.handler.codec.http.HttpResponseStatus
import com.nexus.data.json.{JsonArray, JsonObject}

/**
 * TODO: Edit description
 *
 * @author jk-5
 */
class WebServerHandlerTest extends TWebServerHandler {
  override def handle(request: WebServerRequest, response: WebServerResponse){

    val obj = new JsonObject
    val list = new JsonArray

    list.add("test")
    list.add("test2")
    list.add(1)
    list.add(1.5D)
    list.add(1.62F)
    list.add(4000L)
    list.add(true)
    list.add(false)
    obj.add("list", list)
    obj.add("test", "abcd")

    response.sendHeaders(HttpResponseStatus.OK)
    response.sendData(obj)
    response.close
  }
}
