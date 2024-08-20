#!/usr/bin/env groovy

DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-key') // jenkins에 등록해 놓은 docker hub credentials 이름
//def call(String srcDir, String repositoryName) {
def call() {
    node {
        def home = "${JENKINS_HOME}"
        def destDir = "${WORKSPACE}"
        def buildDir = "${srcDir}" + "/build"
        try {
            //sh "cp -rf ${home}/$buildDir $destDir" // war 파일을 현재 위치로 복사 
            //sh "cp ${home}/$srcDir/Dockerfile $destDir" // Dockerfile
            sh "docker build --platform linux/amd64 -t $repository:$BUILD_NUMBER ."
            sh "docker build --platform linux/amd64 -t $repository:latest ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}