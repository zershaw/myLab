1. aps_development.p12 and aps_distribution.p12 are for pushing notification

2. saleexplore.com.p12 is the file for https configuration

```
openssl pkcs12 -export -in saleexplore.com.crt -inkey saleexplore.com.key -out saleexplore.com.p12 -name saleexplore -CAfile saleexplore_com.ca-bundle -caname root
```

3. google_client_secret is for google oauth login