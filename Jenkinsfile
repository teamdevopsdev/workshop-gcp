podTemplate(
    name: 'android-apk',
    label: 'android-apk', 
    containers: [
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'docker', livenessProbe: containerLivenessProbe(execArgs: '', failureThreshold: 0, initialDelaySeconds: 0, periodSeconds: 0, successThreshold: 0, timeoutSeconds: 0), name: 'docker-container', resourceLimitCpu: '', resourceLimitMemory: '', resourceRequestCpu: '', resourceRequestMemory: '', ttyEnabled: true, workingDir: '/home/jenkins/agent'),
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'gradle:latest', name: 'gradle', ttyEnabled: true)
    ],
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],

) {
    node('android-apk'){

        def REPOS
        def BRANCH = 'main'
        def GIT_REPOS_URL = 'https://github.com/teamdevopsdev/workshop-gcp'

        stage('Checkout Git Repositório'){
            checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'user-github', url: GIT_REPOS_URL]]])
        }

        stage('Verificando Versão'){
            container('gradle') {
                sh 'gradle --version'
            }
        }

        stage('versão'){
            container('gradle') {
                sh 'gradle init'
                sh 'gradle wrapper --gradle-version 7.2'
                sh './gradlew tasks'
            }
        }

        stage('Install AndroidSDK') {
            container('gradle') {
                sh 'wget https://dl.google.com/android/repository/sdk-tools-linux-3859397.zip'
                sh 'mkdir android-sdk'
                sh 'unzip sdk-tools-linux-3859397.zip -d android-sdk'
                sh 'yes | android-sdk/tools/bin/sdkmanager --licenses'
            }
        }

        stage('Credentials') {
            container('gradle') {
                echo "Inicializando Container Gradle"
                sleep(15)
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' apptest/android/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' apptest/android/app/service-account-firebase.json"
                }
            }
        }
        stage('Build') {
            container('gradle') {
                echo "Inicializando Container Gradle"
                sleep(15)
                sh 'cd android-sdk'
                sh './gradlew assembleRelease'
            }
        }
        stage('Gradlew App Distribution') {
                container('gradle') {
                echo "Inicializando Container Android-SDK"
                sleep(15)
                sh './gradlew appDistributionUploadDebug"'
            }
        }
    }
}