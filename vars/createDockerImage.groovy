#!/usr/bin/env groovy

repository = "jhy7342/cicd-study"  //docker hub id와 repository 이름
DOCKERHUB_CREDENTIALS = credentials('docker-hub-access-key') // jenkins에 등록해 놓은 docker hub credentials 이름
def call(String jenkinsHomePath, String srcDir, String repositoryName) {
    node {
         def destDir = sh (
                        script: 'pwd',
                        returnStdout: true
                    ).trim() + "/"
        echo "Destination Dir ::: ${destDir}"
        echo "Source Dir ::: ${srcDir}"
        echo "Repository ::: ${repositoryName}"
        def buildDir = "${srcDir}" + "/build/libs"
        try {
            sh "cp $jenkinsHomePath/$buildDir/*.jar $destDir" // war 파일을 현재 위치로 복사 
            sh "cp $jenkinsHomePath/$srcDir/Dockerfile $destDir" // Dockerfile
            sh "pwd"
            sh "docker build --platform linux/amd64 -t $repository:$BUILD_NUMBER ."
            sh "docker build --platform linux/amd64 -t $repository:latest ."
        } catch (error) {
            echo "[createDockerImage call()] Error ::: ${error}"
        }
    }
}