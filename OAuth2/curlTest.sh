
# Test own OAuth2 authorization server


## Ask for client token (client credential grant type)

`curl acme:acmesecret@localhost:8070/oauth/token -d grant_type=client_credentials`

==>

`{"access_token":"02ffe2f5-574a-42c6-b7ef-58136800f500","token_type":"bearer","expires_in":43199,"scope":"read write"}`


## Ask for final authenticated user token (password grant)

`curl acme:acmesecret@localhost:8070/oauth/token -d grant_type=password -d username=user -d password=f2968c6b-3e1d-4f8c-b6ed-efec93a54d58`

==>

`{"access_token":"69485b47-defa-4a17-bacd-71c13e403380","token_type":"bearer","refresh_token":"609a8d7d-5a01-4379-b156-e310d58c11d9","expires_in":42960,"scope":"read write"}`


## Ask for SSO token (authorization code grant)


