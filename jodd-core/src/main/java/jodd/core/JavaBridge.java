// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Methods that requires different implementations on various Java Platforms.
 */
public class JavaBridge {

	private static final String RESOURCE = JavaBridge.class.getName().replace('.', '/') + ".class";

	/**
	 * Returns urls for the classloader
	 *
	 * @param classLoader classloader in which to find urls
	 * @return list of urls or {@code null} if not found
	 */
	public static URL[] getURLs(final ClassLoader classLoader) {
		if (classLoader instanceof URLClassLoader) {
			URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
			return urlClassLoader.getURLs();
		}

		URL url = currentModuleURL();

		if (url == null) {
			return new URL[0];
		}

		return new URL[] {url};
	}

	private static URL currentModuleURL() {
		URL url = JavaBridge.class.getClassLoader().getResource(RESOURCE);

		if (url == null) {
			return null;
		}

		// use root
		String urlString = url.toString();
		int ndx = urlString.indexOf(RESOURCE);
		urlString = urlString.substring(0, ndx) + urlString.substring(ndx + RESOURCE.length());

		try {
			return new URL(urlString);
		} catch (MalformedURLException ignore) {
			return null;
		}
	}

}