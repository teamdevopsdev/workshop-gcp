pipeline {
    agent any


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

        stage('Lint') {
            steps {
                sh "./gradlew lint"
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew test --stacktrace"
            }
        }

        stage('Credentials') {
            steps {
                withCredentials([file(credentialsId: 'Android-key', variable: 'Android-key')]) {
                    sh "cp '${Android-key}' app/key-teste-pipe.jks"
                    sh "cat app/key-teste-pipe.jks"
                }
                withCredentials([file(credentialsId: 'firebase-test', variable: 'firebase-test')]) {
                    sh "cp '${firebase-test}' app/service-account-firebase.json"
                    sh "cat app/service-account-firebase.json"
                }
            }
        }

        stage('Build') {
            steps {
                sh "./gradlew "
                sh "./gradlew clean assembleRelease"
            }
        }

        stage('Publish') {
            parallel {
                stage('Firebase Distribution') {
                    steps {
                        sh "./gradlew appDistributionUploadRelease"
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
