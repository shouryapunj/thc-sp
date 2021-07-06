node {
	
	dir("C:\Egen\THC\") {
		
	}

	stage("clean workspace") {
		deleteDir()
	}
	
	stage("Run Container on Server") {
		try {
			bat "docker-compose down"
			bat "docker-compose up --build -d"
		
		} catch (e) {
			error "Service update failed"
		}
	
	}

}