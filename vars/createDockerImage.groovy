#!/usr/bin/env groovy

def call(String srcDir, String repositoryName) {
    node {
        def home = "${JENKINS_HOME}"
        def destDir = "${WORKSPACE}"
        def buildDir = "${srcDir}" + "/build/libs"
        try {
            sh "cp -rf ${home}/$buildDir/build $destDir" // war 파일을 현재 위치로 복사 
            sh "cp ${home}/$srcDir/Dockerfile $destDir" // Dockerfile
            sh "docker build --platform linux/amd64 -t $repository:$BUILD_NUMBER ."
            sh "docker build --platform linux/amd64 -t $repository:latest ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}