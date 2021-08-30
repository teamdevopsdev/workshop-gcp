podTemplate(
    name: 'android-apk',
    label: 'android-apk', 
    containers: [
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'docker', livenessProbe: containerLivenessProbe(execArgs: '', failureThreshold: 0, initialDelaySeconds: 0, periodSeconds: 0, successThreshold: 0, timeoutSeconds: 0), name: 'docker-container', resourceLimitCpu: '', resourceLimitMemory: '', resourceRequestCpu: '', resourceRequestMemory: '', ttyEnabled: true, workingDir: '/home/jenkins/agent'),
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'androidsdk/android-30', name: 'android', ttyEnabled: true),
        containerTemplate(args: 'cat', command: '/bin/sh -c', image: 'gradle:latest', name: 'gradle', ttyEnabled: true)
    ],
    volumes: [hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')],
) {
    node('android-apk'){

        def GIT_REPOS_URL = 'https://github.com/teamdevopsdev/workshop-gcp'

        stage('Checkout') {
            checkout([$class: 'GitSCM', branches: [[name: '*/main']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'user-github', url: GIT_REPOS_URL]]])
        }
        
        stage('Install Yarn') {
            container('android') {
                nodejs(nodeJSInstallationName: 'NodeJSv16'){
                    sh 'npm install yarn'  
                }
            }
        }

        stage('Gradlew Init') {
            container('android') {
                sh 'gradle -v'
                sh 'gradle init && gradle wrapper'
                sh './gradlew tasks --all'
            }
        }

        stage('gradle') {
            container('android'){
                withCredentials([file(credentialsId: 'ANDROID_KEYSTORE_FILE', variable: 'ANDROID_KEYSTORE_FILE')]) {
                    sh "cp '${ANDROID_KEYSTORE_FILE}' app-teste/android/app/key-pipe.jks"
                }
                withCredentials([file(credentialsId: 'SERVICE_ACCOUNT_FIREBASE_APP', variable: 'SERVICE_ACCOUNT_FIREBASE_APP')]) {
                    sh " cp '${SERVICE_ACCOUNT_FIREBASE_APP}' app-teste/android/app/service-account-firebase.json"
                }
            }
        }
    }
}
