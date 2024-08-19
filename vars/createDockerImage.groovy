#!/usr/bin/env groovy

repository = "jhy7342/cicd-study"  //docker hub id와 repository 이름
DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-key') // jenkins에 등록해 놓은 docker hub credentials 이름
def call(String jenkinsHomePath, String srcDir, String destDir, String repositoryName) {
    node {
        echo "Source Dir ::: ${srcDir}"
        echo "Destination Dir ::: ${destDir}"
        echo "Repository ::: ${repositoryName}"
        def buildDir = "${srcDir}" + "/build/libs"
        try {
            sh "cp $jenkinsHomePath/$buildDir/*.jar $jenkinsHomePath/$destDir/" // war 파일을 현재 위치로 복사 
            sh "cp $jenkinsHomePath/$srcDir/Dockerfile $jenkinsHomePath/$destDir/" // Dockerfile
            sh "cd $jenkinsHomePath/$destDir"
            sh "pwd"
            sh "docker build --platform linux/amd64 -t $repository:$BUILD_NUMBER ."
            sh "docker build --platform linux/amd64 -t $repository:latest ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}