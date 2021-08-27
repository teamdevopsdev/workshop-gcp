pipeline {
    agent any

    tools {
        gradle 'Gradle-7.2'
        nodejs 'NodeJSv16'

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

        stage('Install Android SDK') {
            steps {
                sh 'sudo wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip'
                sh 'mkdir android-sdk'
                sh 'unzip sdk-tools-linux-3859397.zip -d android-sdk'
                sh 'yes | android-sdk/tools/bin/sdkmanager --licenses'
            }
        }

        stage('Install Yarn') {
            steps {
                    sh 'npm install --global yarn'
            }
        }

        stage('Versão Gradle') {
            steps {
                sh 'gradle -v'
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' app-teste/android/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' app-teste/android/app/service-account-firebase.json"
                }
            }
        }

        stage('Gradlew Init') {
            steps {
                sh 'yarn'
                sh 'cd app-teste/android'
                sh 'yarn'
                sh 'gradle init && gradle wrapper'
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
