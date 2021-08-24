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

        stage('Version Gradle') {
            steps {
                sh 'gradle -v'
            }
        }

        stage('Gradlew build') {
            steps {
                sh './gradlew build'
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' hello-word/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' hello-word/app/service-account-firebase.json"
                }
            }
        }

        stage('Tasks') {
            steps{
                sh 'gradle wrapper --gradle-version 7.2'
                sh './gradlew test'
                sh './gradlew tasks'

            }
        }

        stage('gradlew teste'){
            steps{
                sh './gradlew teste --stacktrace'
            }
        }

        stage('Build') {
            steps {
                sh 'cd /var/jenkins_home/workspace/Android-sdk/android-sdk'
                sh " ./gradlew assembleRelease"
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
