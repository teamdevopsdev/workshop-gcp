pipeline {
    agent any

    tools {
        gradle 'Gradle-7.2'
        nodejs 'NodeJSv16'
    }

    environment {
        branch = 'main'
        url = 'https://github.com/teamdevopsdev/workshop-gcp.git'
    }

    stages {

        stage('Checkout git') {
            steps {
                git branch: branch, credentialsId: 'user-github', url: url
            }
        }

        stage('Install and Init Yarn') {
            steps {
                    sh 'npm install --global yarn'
                    sh 'cd app-teste/android && yarn'
            }
        }

        stage('Version Gradle') {
            steps {
                sh 'gradle -v'
            }
        }

        stage('Install Android SDK') {
            steps {
                sh 'wget https://dl.google.com/android/repository/commandlinetools-linux-7583922_latest.zip'
                sh 'mkdir android-sdk'
                sh 'unzip commandlinetools-linux-7583922_latest.zip -d android-sdk'
                sh 'ls'
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
                sh 'cd app-teste/android && chmod +x gradlew && gradle wrapper && ./gradlew tasks && ./gradlew assembleRelease'
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
