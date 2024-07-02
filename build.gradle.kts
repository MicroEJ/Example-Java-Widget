/*
 * Kotlin
 *
 * Copyright 2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

plugins {
	id("com.microej.gradle.application") version "0.15.0"
}

group = "com.microej.example.ui"
version = "8.1.0"

microej {
	applicationMainClass = "com.microej.demo.widget.common.Navigation"
}

dependencies {
	implementation("ej.api:edc:1.3.5")
	implementation("ej.api:microui:3.1.0")
	implementation("ej.api:drawing:1.0.2")
	implementation("ej.library.ui:widget:5.2.0")
	implementation("ej.library.runtime:basictool:1.5.0")
	implementation("ej.library.runtime:service:1.1.1")
	implementation("ej.library.eclasspath:collections:1.4.0")
	implementation("ej.library.eclasspath:stringtokenizer:1.2.0")

	microejVee("com.microej.veeport.st.stm32f7508-dk:M5QNX_eval:2.2.0")
}

tasks.withType<Javadoc> {
	options.encoding = "UTF-8"
}

testing {
	suites {
		val test by getting(JvmTestSuite::class) {
			microej.useMicroejTestEngine(this)

			dependencies {
				implementation(project())
				implementation("ej.api:edc:1.3.5")
				implementation("ej.api:microui:3.1.0")
				implementation("ej.api:drawing:1.0.2")
				implementation("ej.library.test:junit:1.7.1")
				implementation("ej.library.runtime:basictool:1.5.0")
				implementation("org.junit.platform:junit-platform-launcher:1.8.2")
			}
		}
	}
}