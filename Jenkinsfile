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
                    sh "cat hello-word/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'firebase-test', variable: 'firebase-test')]) {
                    sh " cp '${firebase-test}' hello-word/app/service-account-firebase.json"
                    sh " cat hello-word/app/service-account-firebase.json"
                }
            }
        }

        stage('Build') {
            steps {
                sh " gradle assembleDebug"
            }
        }

        stage('Publish') {
            parallel {
                stage('Firebase Distribution') {
                    steps {
                        sh " gradle appDistributionUploadDebug"
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
