podTemplate(
    name: 'android-apk',
    label: 'android-apk', 
    containers: [
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'docker', livenessProbe: containerLivenessProbe(execArgs: '', failureThreshold: 0, initialDelaySeconds: 0, periodSeconds: 0, successThreshold: 0, timeoutSeconds: 0), name: 'docker-container', resourceLimitCpu: '', resourceLimitMemory: '', resourceRequestCpu: '', resourceRequestMemory: '', ttyEnabled: true, workingDir: '/home/jenkins/agent'),
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'androidsdk/android-30', name: 'android-sdk', ttyEnabled: true),
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

        stage('Install Gradle'){
            container('android-sdk') {
                sh 'wget https://services.gradle.org/distributions/gradle-7.2-bin.zip'
                sh 'mkdir /opt/gradle'
                sh 'unzip -d /opt/gradle gradle-7.2-bin.zip'
                sh 'ls /opt/gradle/gradle-7.2'
            }
        }

        stage('versão'){
            container('android-sdk') {
                sh 'gradle -v'
                sh 'chmod +x gradlew'
                sh 'gradle wrapper --gradle-version 7.2'
                sh 'gradle init'
                sh './gradlew tasks'
            }
        }

        stage('Aceitando termos do SDK') {
            container('android-sdk') {
                sh 'yes | sdkmanager'
            }
        }

        stage('Credentials') {
            container('android-sdk') {
                echo "Inicializando Container Gradle"
                sleep(15)
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' hello-word/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' hello-word/app/service-account-firebase.json"
                }
            }
        }
        stage('Build') {
            container('android-sdk') {
                echo "Inicializando Container Gradle"
                sleep(15)
                sh './gradlew assembleRelease'
                //sh './gradlew assembleDebug'
            }
        }
        stage('Gradlew Lint') {
                container('android-sdk') {
                echo "Inicializando Container Android-SDK"
                sleep(15)
                sh './gradlew appDistributionUploadDebug"'
            }
        }
    }
}