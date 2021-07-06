node {


	stage("clean workspace") {
		deleteDir()
	}
	
	stage("Run Container on Server") {
		try {
			sh "docker-compose down"
			sh "docker-compose up --build -d"
		
		} catch (e) {
			error "Service update failed"
		}
	
	}

}