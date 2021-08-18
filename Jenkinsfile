podTemplate(
    name: 'android-firebase'
    label: 'android', 
    containers: [
        containerTemplate(args: 'cat', name: 'docker', command: '/bin/sh -c', image: 'docker', ttyEnabled: true)
    ],
    volumes: [
      hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock')
    ]
)
    node('android') {
        environment {
            APP_NAME = 'test'
        }
        
        options {
        // Stop the build early in case of compile or test failures
        skipStagesAfterUnstable()
        }
        stage('Detect build type') {
            steps {
                script { 
                    if (env.BRANCH_NAME == 'devops' || env.CHANGE_TARGET == 'devops') {
                        env.BUILD_TYPE = 'devops'
              //  } else if (env.BRANCH_NAME == 'master' || env.CHANGE_TARGET == 'master') {
              //      env.BUILD_TYPE = 'release'
                }
                }
            }
        }

        stage('Compile') {
            steps {
                // Compile the app and its dependencies
                sh './gradlew compile${BUILD_TYPE}Sources'
            }
        }
        
        stage('Build') {
            steps {
                // Compile the app and its dependencies
                sh './gradlew assemble${BUILD_TYPE}'
                sh './gradlew generatePomFileForLibraryPublication'
            }
        }

    }