#!/usr/bin/env bash
scp -i aws/KeyPair-chainboats.pem ../target/saleexplore-api-server-1.0.0.jar ubuntu@api.saleexplore.com:~
#java -jar saleexplore-api-server-1.0.0.jar --spring.profiles.active=prod

# Upload the lucene index files
#scp -i aws/KeyPair-chainboats.pem -r ../lucene_index ubuntu@52.76.87.23:~


# SSH to the server and restart the service
ssh -i aws/KeyPair-chainboats.pem ubuntu@api.saleexplore.com
# We installed the redis server on api.saleexplore.com
sudo service discountserver restart