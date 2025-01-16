# Running the application infrasctructure

To create productive environment to deploy the application we need to create infra.  
To do it is needed to run the command in the root folder:  
```bash
 aws cloudformation create-stack --stack-name TreasuryApiEbnzRiacho --template-body file:///Users/joabechaves/Documents/developments/church-financial/backend/cloudformation/template.yaml --capabilities CAPABILITY_NAMED_IAM
```
this command will provide all the infra to run the application.
