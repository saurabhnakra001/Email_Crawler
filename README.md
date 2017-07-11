

# Synopsis
This project focuses on making an Email Crawler which can be used for fetching emails and their attachments from a given mailing list available on an archive(for e.g.   http://mail-archives.apache.org/)

# How to use
Import into intelliJ Idea using following steps
* Go to VCS->Checkout from Version Control->Github
* paste URL [https://github.com/saurabhnakra001/Email_Crawler.git](https://github.com/saurabhnakra001/Email_Crawler.git) and clone repository into your account
* after successful import, you can run Main.java file 

# Overview
Given a mailing list and access to the archive store, get the information of how the emails are arranged
Depending on the previous step, make the folder structure locally
Download whatever is the unit of storage (for e.g - single mbox files)
Log the details for reference

## Inputs :

* Link to the archive
* Pattern of mails storage (working on it to auto figure out)

## Outputs :

* Information of which files are downloded, which alraedy existed, and which are updated.
* Mails and their attachments in a certain format (Currently in month-wise view)

## Errors Handled:

* Link to the archive is not able to connect
* The pattern of mails storage does not apply to our assumption
* No permission of making folder in local machine
* Internet Connectivity is lost meanwhile fetching the details

## Functionalities :

* Update current month mailing list
* Should be able to log all types of errors
