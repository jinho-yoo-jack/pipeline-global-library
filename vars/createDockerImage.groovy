#!/usr/bin/env groovy

DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-key') // jenkins에 등록해 놓은 docker hub credentials 이름
//def call(String srcDir, String repositoryName) {
def call() {
    // def home = "${JENKINS_HOME}"
    // def destDir = "${WORKSPACE}"
    // def buildDir = "${srcDir}" + "/build"
    dir("${params.workspace}") {                    
        try {
            //sh "cp -rf ${home}/$buildDir $destDir" // war 파일을 현재 위치로 복사 
            //sh "cp ${home}/$srcDir/Dockerfile $destDir" // Dockerfile
            sh "docker build --no-cache --platform linux/amd64 -t $DOCKERHUB_REPOSITORY ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}