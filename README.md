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

## oauth

See also:
* <https://developers.google.com/api-client-library/java/google-api-java-client/oauth2>

### authorize

The sub command `oauth authorize` updates your credential stored in the `${GoogleAPI.dataStoreDirectoryPath}/StoredCredential`.

    $ google-cli oauth authorize <scope>

The 1st argument `<scope>` must be given as one of the OAuth 2.0 scopes to access Google APIs. For instance, if you want to access some files in Google Drive, the value should be one of the scopes described in the following URL:

* <https://developers.google.com/identity/protocols/googlescopes#drivev3>

For just read-only access to Google Drive, the scope should be `https://www.googleapis.com/auth/drive.readonly`.

    $ google-cli oauth authorize "https://www.googleapis.com/auth/drive.readonly"

This command will launch a LocalServerReceiver (Jetty) to receive an OAuth2.0 token from Google Sign-in.

## sheets

See also:
* <https://developers.google.com/sheets/api/reference/rest/>

### get

    # Print out all the rows of the columns between A and E of <sheet> in the <sheedId>
    $ google-cli get <sheetId> <sheet>'!A:E'

See also:
* <https://developers.google.com/sheets/api/reference/rest/v4/spreadsheets/get>

