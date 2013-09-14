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

package com.nexus.data.json

import java.io.Reader
import com.nexus.webserver.TWebServerResponse
import java.util
import scala.collection.mutable.ListBuffer

object JsonArray {
  def readFrom(reader: Reader) = JsonValue.readFrom(reader).asArray
  def readFrom(string: String) = JsonValue.readFrom(string).asArray
  implicit def asJsonArray(c: util.Collection[_]): JsonArray = {
    val a = new JsonArray
    for(o <- c){
      o match{
        case i: Int => a.add(i)
        case l: Long => a.add(l)
        case f: Float => a.add(f)
        case d: Double => a.add(d)
        case b: Boolean => a.add(b)
        case s: String => a.add(s)
        case v: JsonValue => a.add(v)
        case e => throw new RuntimeException("Unsupported conversion, from %s to JsonValue".format(e.getClass.getSimpleName))
      }
    }
    a
  }
}

class JsonArray extends JsonValue with Iterable[JsonValue] with TWebServerResponse {

  private final val values = ListBuffer[JsonValue]()

  def add(value: Int): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: Long): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: Float): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: Double): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: Boolean): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: String): JsonArray = {
    this.values += JsonValue.valueOf(value)
    this
  }

  def add(value: JsonValue): JsonArray = {
    if(value == null) throw new NullPointerException("value is null")
    this.values += value
    this
  }

  def set(index: Int, value: Long): JsonArray = {
    this.values.update(index, JsonValue.valueOf(value))
    this
  }

  def set(index: Int, value: Float): JsonArray = {
    this.values.update(index, JsonValue.valueOf(value))
    this
  }

  def set(index: Int, value: Double): JsonArray = {
    this.values.update(index, JsonValue.valueOf(value))
    this
  }

  def set(index: Int, value: Boolean): JsonArray = {
    this.values.update(index, JsonValue.valueOf(value))
    this
  }

  def set(index: Int, value: String): JsonArray = {
    this.values.update(index, JsonValue.valueOf(value))
    this
  }

  def set(index: Int, value: JsonValue): JsonArray = {
    if(value == null) throw new NullPointerException("value is null")
    this.values.update(index, value)
    this
  }

  override def size: Int = this.values.size
  override def isEmpty: Boolean = this.values.isEmpty

  def get(index: Int): JsonValue = this.values(index)
  def getValues = this.values.toList

  def iterator: Iterator[JsonValue] = {
    val iterator: Iterator[JsonValue] = this.values.iterator
    new Iterator[JsonValue] {
      def hasNext: Boolean = iterator.hasNext
      def next(): JsonValue = iterator.next()
      def remove() = throw new UnsupportedOperationException
    }
  }
  private [json] def write(writer: JsonWriter) = writer.writeArray(this)
  override def isArray = true
  override def asArray = this
  override def hashCode = this.values.hashCode()
  override def equals(obj: Any): Boolean =
    if(obj == null) false
    else if(this.getClass ne obj.getClass) false
    else this.values == obj.asInstanceOf[JsonArray].values

  def getResponseData: String = this.toString()
  def getMimeType = "application/json"
}