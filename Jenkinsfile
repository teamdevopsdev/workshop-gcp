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
                sh 'wget https://redirector.gvt1.com/edgedl/android/studio/ide-zips/2020.3.1.23/android-studio-2020.3.1.23-linux.tar.gz'
                sh 'tar -vzxf android-studio-2020.3.1.23-linux.tar.gz'
                sh 'yes | sdkmanager --licences'
                sh 'mv android-sdk /opt/'
                sh 'export ANDROID_SDK_ROOT=/opt/android-sdk'
                sh 'echo "export ANDROID_SDK_ROOT=/opt/android-sdk" >> ~/.bashrc'
                sh 'echo "export PATH=$PATH:$ANDROID_SDK_ROOT/tools" >> ~/.bashrc'
                sh 're-login'
                sh 'cd /opt/android-sdk/tools/bin'
            }
        }

        stage('Credentials') {
            container('gradle') {
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
            container('gradle') {
                echo "Inicializando Container Gradle"
                sleep(15)
                sh './gradlew assembleRelease'
                //sh './gradlew assembleDebug'
            }
        }
        stage('Gradlew Lint') {
                container('gradle') {
                echo "Inicializando Container Android-SDK"
                sleep(15)
                sh './gradlew appDistributionUploadDebug"'
            }
        }
    }
}