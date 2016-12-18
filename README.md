# Google API Command Line Tool

## Configuration

Make sure you already have a Google API project and a client ID. You are not familiar with the Google API using OAuth2.0, please consult the following URL.

* https://developers.google.com/identity/protocols/OAuth2

The skelton file `google-cli-main/skel/resources/application.conf` is prepared for your configuration file, written in [Typesafe Config](https://typesafehub.github.io/config/) HOCON format.

    GoogleAPI {
      # The path to your client ID file downloaded from Google API console
      clientSecretPath: "/path/to/client_secret.json"
      # The path to a data store directory for StoredCredential
      dataStoreDirectoryPath: "/path/to/google/data/dir"
    }

Copy this skelton file into `~/.google-cli/application.conf`, and replace those values with yours.
