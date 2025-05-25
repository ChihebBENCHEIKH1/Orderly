FROM mongo:latest

COPY stock.json /stock.json

CMD mongoimport --host mongo-customer --db warehouse --collection stock --drop --file /stock.json --jsonArray