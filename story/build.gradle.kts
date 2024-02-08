plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("maven-publish")
}

android {
    namespace = "com.yavuzmobile.story"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.yavuzmobile.story"
            artifactId = "story-view"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name = "Android Story View Library"
                description = "A library for displaying stories in Android apps."
                url = "https://github.com/mstfyvz/story-view"
                licenses {
                    license {
                        name = "Apache-2.0"
                        url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                    }
                }
                developers {
                    developer {
                        id = "yavuz-mobile"
                        name = "Yavuz Mobile"
                        email = "developer.mustafayavuz@gmail.com"
                    }
                }
                scm {
                    connection = "scm:git:https://github.com/mstfyvz/story-view.git"
                    developerConnection = "scm:git:https://github.com/mstfyvz/story-view.git"
                    url = "https://github.com/ymstfyvz/story-view"
                }
            }
        }
    }
}