/*
 * Kotlin
 *
 * Copyright 2024-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */

plugins {
	id("com.microej.gradle.application") version libs.versions.microej.sdk
}

group = "com.microej.example.ui"
version = "8.1.2"

microej {
	applicationEntryPoint = "com.microej.demo.widget.common.Navigation"
}

dependencies {
	implementation(libs.api.edc)
	implementation(libs.api.microui)
	implementation(libs.api.drawing)
	implementation(libs.library.widget)
	implementation(libs.library.basictool)
	implementation(libs.library.service)
	implementation(libs.library.collections)
	implementation(libs.library.stringtokenizer)

	microejVee(libs.vee.port.st.stm32f7508)
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
				implementation(libs.api.edc)
				implementation(libs.api.microui)
				implementation(libs.api.drawing)
				implementation(libs.library.basictool)
				implementation(libs.junit)
				implementation(libs.junit.platform)
			}
		}
	}
}
