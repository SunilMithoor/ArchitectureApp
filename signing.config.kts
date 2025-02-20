//android {
//
//    val propsFile = rootProject.file("keystore.properties")
//
//    signingConfigs {
//        create("release") {
//            if (propsFile.exists()) {
//                val props = Properties().apply {
//                    load(FileInputStream(propsFile))
//                }
//                storeFile = file(props.getProperty("releaseStoreFile"))
//                storePassword = props.getProperty("releaseStorePassword")
//                keyAlias = props.getProperty("releaseKeyAlias")
//                keyPassword = props.getProperty("releaseKeyPassword")
//            }
//        }
//    }
//
//    buildTypes {
//        debug {
//            isMinifyEnabled = false
//        }
//
//        release {
//            isMinifyEnabled = true
//            signingConfig = signingConfigs.getByName("release")
//        }
//    }
//}
//
//
////
////android {
////
////    def propsFile = file("../keystore.properties")
////
////    signingConfigs {
////        release {
////            if (propsFile.exists()) {
////                def props = new Properties()
////                props.load(new FileInputStream(propsFile))
////                storeFile file(props['releaseStoreFile'])
////                storePassword props['releaseStorePassword']
////                keyAlias props['releaseKeyAlias']
////                keyPassword props['releaseKeyPassword']
////            }
////        }
//////        debug {
//////            if (propsFile.exists()) {
//////                def props = new Properties()
//////                props.load(new FileInputStream(propsFile))
//////                storeFile file(props['debugStoreFile'])
//////                storePassword props['debugStorePassword']
//////                keyAlias props['debugKeyAlias']
//////                keyPassword props['debugKeyPassword']
//////            }
//////        }
////    }
////
////    buildTypes {
////        debug {
//////            if (propsFile.exists()) {
//////                productFlavors.stage.signingConfig signingConfigs.debug
//////                productFlavors.prod.signingConfig signingConfigs.debug
//////            }
////        }
////
//////        release {
//////            signingConfig = signingConfigs.getByName("release")
//////        }
////
////        release {
////            if (propsFile.exists()) {
////                productFlavors.stage.signingConfig signingConfigs.debug
////                productFlavors.prod.signingConfig signingConfigs.release
////            }
////        }
////    }
////}
