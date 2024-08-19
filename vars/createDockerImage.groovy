#!/usr/bin/env groovy

repository = "jhy7342/cicd-study"  //docker hub id와 repository 이름
DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-key') // jenkins에 등록해 놓은 docker hub credentials 이름
def call(String srcDir, String destDir, String repositoryName) {
    node {
        echo "Source Dir ::: ${srcDir}"
        echo "Destination Dir ::: ${destDir}"
        echo "Repository ::: ${repositoryName}"
        def buildDir = "${srcDir}" + "/build/libs"
        try {
            sh "cp /var/jenkins_home/workspace/$buildDir/*.jar /var/jenkins_home/workspace/$destDir/" // war 파일을 현재 위치로 복사 
            sh "cp /var/jenkins_home/workspace/$srcDir/Dockerfile /var/jenkins_home/workspace/$destDir/" // Dockerfile
            sh "pwd"
            sh "docker build --platform linux/amd64 -t $repository:$BUILD_NUMBER ."
            sh "docker build --platform linux/amd64 -t $repository:latest ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}