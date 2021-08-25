podTemplate(
    name: 'android-apk',
    label: 'android-apk', 
    containers: [
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'docker', livenessProbe: containerLivenessProbe(execArgs: '', failureThreshold: 0, initialDelaySeconds: 0, periodSeconds: 0, successThreshold: 0, timeoutSeconds: 0), name: 'docker-container', resourceLimitCpu: '', resourceLimitMemory: '', resourceRequestCpu: '', resourceRequestMemory: '', ttyEnabled: true, workingDir: '/home/jenkins/agent'),
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'androidsdk/android-30', name: 'android-sdk', ttyEnabled: true),
    ],
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
) {
    node('android-apk'){
        tools {
            gradle 'Gradle-7.2'
        }

        def REPOS
        def BRANCH = 'main'
        def GIT_REPOS_URL = 'https://github.com/teamdevopsdev/workshop-gcp'

        stage('Checkout Git Repositório'){
            checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'user-github', url: GIT_REPOS_URL]]])
        }

        stage('Gradlew Lint') {
            container('android-sdk') {
            echo "Inicializando Container Android-SDK"
            sleep(15)
            sh './gradlew lint'    
            }
        }

        stage('Gradlew Test') {
            container('android-sdk') {
            echo "Inicializando Container Android-SDK"
            sleep(15)
            sh './gradlew test --stacktrace'
            }

        }
        stage('Credentials') {
            container('android-sdk') {
            echo "Inicializando Container Android-SDK"
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
            echo "Inicializando Container Android-SDK"
            sleep(15)
            sh './gradlew assembleRelease'
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