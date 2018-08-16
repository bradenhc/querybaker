/*
 * Copyright 2018 Braden Hitchcock
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.bradenhc.querybaker.api;

/**
 * Bread and butter interface for the QueryBaker project. All query builders implement this interface. The interface
 * defines common functionality among all query builders so that they can correctly return a database query string.
 */
public interface IQueryBuilder {

	/**
	 * Generates a query string to be used in a database transaction.
	 * 
	 * @return a string representing a database query built based on the property settings of the class that implements
	 *         this method
	 */
	public String build();

}
