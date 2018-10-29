# Contributing Guide
## Questions
You can drop by the [Discord server](https://discord.gg/yzbuDU4) to ask any questions or join in on discussions on the 
direction and goals of the wrapper.

Ask your questions in `#wrapper-help` and you will receive a reply.

Any issues opened must be specifically targeted at this project's source code, nothing else.

## Tools
### Access Token Generator
It is too tedious to set up a access token every time you want to work on the library, so Woo Jia Hao has created a tool
from JavaFX that assists you in generating and managing the access tokens so you can focus on simply making a single
SpotifyUser object and managing that single access token.

The link to the tool's repository can be found [here](https://github.com/woojiahao/spotify-access-token-generator)

So, your workflow when testing whether endpoints are working should look like this:

```java
SpotifyUser user = new SpotifyUser("<access token generated>");
//...
```

## Contribution quality
### Commits
1. Make small atomic commits
2. Each commit **must** have a meaningful description

### Pull requests
1. Targets a specific part of the application
2. No major code rewrites, if you wish to perform a major code rewrite, please make an issue **first** and await approval before beginning
3. Properly described 
4. Reference the issue that this PR was targeted at, if possible

## Finding issues
If you wish to complete an issue, please leave a comment on the issue and see if anyone else is working on it, if there is someone else doing this issue, you could request for a collaboration or pick another issue.

### Beginners
If you are a beginner, pick the issues with the label `good-first-issue` and try to solve it, you can always request for assistance if you encounter any roadblocks along the way.

## Making a pull request
### First time contributors
1. Fork the repository
2. Clone it onto your local machine
   ```bash
   $ git clone https://github.com/<your name>/java-spotify-wrapper.git
   $ cd java-spotify-wrapper
   ```
3. Add a remote to the primary repository
   ```bash
   $ git remote add upstream https://github.com/woojiahao/java-spotify-wrapper.git
   ```
4. Make the desired changes on your local copy and commit them
   ```bash
   $ git add .
   $ git commit -m "Some changes"
   ```
5. Push these changes to your local repository
   ```bash
   $ git push -u origin master
   ```
6. Go to your copy of the repository on GitHub and open a pull request to the `master` branch of the primary repository

### Returning contributors
1. Pull the latest changes from the primary repository
   ```bash
   $ git pull upstream master
   ```
2. Make the desired changes on your local copy and commit them
   ```bash
   $ git add .
   $ git commit -m "Some more changes"
   ```
3. Push these changes to your local repository
   ```bash
   $ git push
   ```
4. Go to your copy of the repository on GitHub and open a pull request to the `master` branch of the primary repository

## Making a good issue
There is no strict format for making issues, however, the following points are parts to include when making an issue.
### Reporting a bug
1. Description of the problem
2. Steps to replicate the problem (if possible)
3. Show any error messages in the console that appeared due to the problem

### Feature request
1. Description of the feature
2. Sample code of what it could look like (if possible)
3. Open a PR for the implementation (if possible)

## Code of conduct
A copy of the code of conduct for this project can be found [here.](https://github.com/woojiahao/java-spotify-wrapper/blob/master/CODE_OF_CONDUCT.md)