pipeline {
    agent any

    tools {
        gradle 'Gradle-7.2'
    }

    environment {
        branch = 'main'
        url = 'https://github.com/teamdevopsdev/workshop-gcp'
    }

    stages {

        stage('Checkout git') {
            steps {
                git branch: branch, credentialsId: 'user-github', url: url
            }
        }
        // stage('Build') {
        //     steps {
        //         nodejs(nodeJSInstallationName: 'NodeJSv16.8 Installation') {
        //             sh 'npm config ls'
        //         }
        //     }
        // }

        stage('Version Gradle') {
            steps {
                sh 'gradle -v'
            }
        }

        stage('Gradlew Init') {
            steps {
                sh 'gradle init'
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' app-teste/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' app-teste/app/service-account-firebase.json"
                }
            }
        }

        stage('gradlew teste'){
            steps{
                sh 'cd app-teste/android && gradle wrapper && ./gradlew tasks --all'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew assembleRelease'
            }
        }

        stage('Publish') {
            parallel {
                stage('Firebase Distribution') {
                    steps {
                        sh "./gradlew appDistributionUploadDebug"
                    }
                }

                stage('Google Play...') {
                    steps {
                        sh "echo 'Test...'"
                    }
                }
            }
        }
    }
}
