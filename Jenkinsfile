node {
	
	dir("C:\\Egen\\THC\\") {

	    stage("Packaging application") {
	        try {
	            bat "mvn clean package"
	        } catch (e) {
	            error "Packaging failed"
	        }
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

}