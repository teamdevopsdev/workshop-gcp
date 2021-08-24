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
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' hello-word/app/service-account-firebase.json"
                    sh " cat hello-word/app/service-account-firebase.json"
                }
            }
        }

        stage('Tasks') {
            steps{
                sh './gradlew tasks'
            }
        }

        stage('Build') {
            steps {
                sh " ./gradlew assembleDebug"
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
