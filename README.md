
# Jwt authentcation/authorization with Spring

This is a template for jwt auth using auth0 jwt dependency and spring security. AuthenticationFilter generates access and refresh tokens when atttempting login. AuthorizationFilter runs every requests and checks if the access token is present and updates security context.

Use @PreAuthorize annotation on your methods to grant access to users with specific roles.

