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

        stage('Init') {
            steps {
                sh "gradle init"
            }
        }

        stage('Test') {
            steps {
                sh "gradle test --stacktrace"
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
                sh "gradle "
                sh "gradle clean assembleRelease"
            }
        }

        stage('Publish') {
            parallel {
                stage('Firebase Distribution') {
                    steps {
                        sh "gradle appDistributionUploadRelease"
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
