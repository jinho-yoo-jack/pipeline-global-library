#!/usr/bin/env groovy

def call(String repositoryURL) {
    node {
        def branches = []
        try {
            branches = withCredentials([gitUsernamePassword(credentialsId: 'ea380414-e517-45cb-91dc-882c64c04255', gitToolName: 'Default')]) {
                return sh(script: "git ls-remote -h ${repositoryURL} | sed 's?.*refs/heads/??' ", returnStdout: true).trim()
            }
        } catch (error) {
            echo "[getBranchList call()] Error ::: ${error}"
        }
        return branches
    }
}
