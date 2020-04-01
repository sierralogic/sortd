# sortd

Sortd loads, parses, and sorts delimited data files and HTTP payloads.

The `sortd` utility/service supports three (3) formats:
- comma-delimited (csv) : ex `"FirstName, LastName, Hobby"`
- pipe-delimited (`|`) : ex. `"FirstName | LastName | Hobby"`
- space-delimited : ex. `"FirstName LastName Hobby"`

NOTE: The delimiter is assumed to **NOT** be in the data or header values.

## Command Line

In the root folder of the `sortd` project:

```text
$ ./sortd

Usage: sortd <data-delimited-filename> [<sort-by-field: may be name, dob, or gender>]

If the 'sort-by-field` is missing or invalid, name is used for sorting.
```

```text
$ ./sortd data/records.csv

LastName | FirstName | Gender | FavoriteColor | DateOfBirth
===========================================================
Bukowski | Charles | M | Brown | 08/16/1920
Sexton | Anne | F | Black | 11/09/1928
Shamaya | Otep | F | Purple | 09/07/1979
Wright | Richard | M | Yellow | 09/04/1908

```

```text
$ ./sortd data/records.csv gender

LastName | FirstName | Gender | FavoriteColor | DateOfBirth
===========================================================
Sexton | Anne | F | Black | 11/09/1928
Shamaya | Otep | F | Purple | 09/07/1979
Bukowski | Charles | M | Brown | 08/16/1920
Wright | Richard | M | Yellow | 09/04/1908

```

```text
$ ./sortd data/records.csv dob

LastName | FirstName | Gender | FavoriteColor | DateOfBirth
===========================================================
Wright | Richard | M | Yellow | 09/04/1908
Bukowski | Charles | M | Brown | 08/16/1920
Sexton | Anne | F | Black | 11/09/1928
Shamaya | Otep | F | Purple | 09/07/1979

```

```text
$ ./sortd data/records.csv name

LastName | FirstName | Gender | FavoriteColor | DateOfBirth
===========================================================
Bukowski | Charles | M | Brown | 08/16/1920
Sexton | Anne | F | Black | 11/09/1928
Shamaya | Otep | F | Purple | 09/07/1979
Wright | Richard | M | Yellow | 09/04/1908

```

```text
$ ./sortd data/file-not-found

Error loading and parsing data file 'blah'.

Error processing/parsing/loading filename 'blah': java.io.FileNotFoundException: blah (No such file or directory)

Usage: sortd <data-delimited-filename> [<sort-by-field: may be name, dob, or gender>]

If the 'sort-by-field` is missing or invalid, name is used for sorting.

```

## REST API

### Load Data

#### POST "/records"

Loads/parses the payload with the first line before the headers and the remaining
lines being the delimited data.

##### Payload

```
LastName, FirstName, Gender, FavoriteColor, DateOfBirth
Sexton, Anne, F, Black, 1928/11/09
Bukowski, Charles, M, Brown, 1920/08/16
Wright, Richard, M, Yellow, 1908/09/04
Shamaya, Otep, F, Purple, 1979/09/07
```

##### Response

```json
{
   "message":"Data parsed/loaded successfully.",
   "records":[
      {
         "LastName":"Sexton",
         "FirstName":"Anne",
         "Gender":"F",
         "FavoriteColor":"Black",
         "DateOfBirth":"1928/11/09"
      },
      {
         "LastName":"Bukowski",
         "FirstName":"Charles",
         "Gender":"M",
         "FavoriteColor":"Brown",
         "DateOfBirth":"1920/08/16"
      },
      {
         "LastName":"Wright",
         "FirstName":"Richard",
         "Gender":"M",
         "FavoriteColor":"Yellow",
         "DateOfBirth":"1908/09/04"
      },
      {
         "LastName":"Shamaya",
         "FirstName":"Otep",
         "Gender":"F",
         "FavoriteColor":"Purple",
         "DateOfBirth":"1979/09/07"
      }
   ],
   "delimiter-type":"comma",
   "headers":[
      "LastName",
      "FirstName",
      "Gender",
      "FavoriteColor",
      "DateOfBirth"
   ],
   "field-count":5,
   "record-count":4
}
```

### Retrieve Sorted Records

#### GET "/records/gender"

Returns records sorted by `gender`.

##### Response

```json
{
   "records":[
      {
         "LastName":"Sexton",
         "FirstName":"Anne",
         "Gender":"F",
         "FavoriteColor":"Black",
         "DateOfBirth":"11/09/1928"
      },
      {
         "LastName":"Shamaya",
         "FirstName":"Otep",
         "Gender":"F",
         "FavoriteColor":"Purple",
         "DateOfBirth":"09/07/1979"
      },
      {
         "LastName":"Bukowski",
         "FirstName":"Charles",
         "Gender":"M",
         "FavoriteColor":"Brown",
         "DateOfBirth":"08/16/1920"
      },
      {
         "LastName":"Wright",
         "FirstName":"Richard",
         "Gender":"M",
         "FavoriteColor":"Yellow",
         "DateOfBirth":"09/04/1908"
      }
   ]
}
```

#### GET "/records/birthdate"

Returns records sorted by `birthdate`.

##### Response

```json
{
   "records":[
      {
         "LastName":"Sexton",
         "FirstName":"Anne",
         "Gender":"F",
         "FavoriteColor":"Black",
         "DateOfBirth":"11/09/1928"
      },
      {
         "LastName":"Shamaya",
         "FirstName":"Otep",
         "Gender":"F",
         "FavoriteColor":"Purple",
         "DateOfBirth":"09/07/1979"
      },
      {
         "LastName":"Bukowski",
         "FirstName":"Charles",
         "Gender":"M",
         "FavoriteColor":"Brown",
         "DateOfBirth":"08/16/1920"
      },
      {
         "LastName":"Wright",
         "FirstName":"Richard",
         "Gender":"M",
         "FavoriteColor":"Yellow",
         "DateOfBirth":"09/04/1908"
      }
   ]
}
```

#### GET "/records/name"

Returns records sorted by `name`.

##### Response

```json
{
   "records":[
      {
         "LastName":"Bukowski",
         "FirstName":"Charles",
         "Gender":"M",
         "FavoriteColor":"Brown",
         "DateOfBirth":"08/16/1920"
      },
      {
         "LastName":"Sexton",
         "FirstName":"Anne",
         "Gender":"F",
         "FavoriteColor":"Black",
         "DateOfBirth":"11/09/1928"
      },
      {
         "LastName":"Shamaya",
         "FirstName":"Otep",
         "Gender":"F",
         "FavoriteColor":"Purple",
         "DateOfBirth":"09/07/1979"
      },
      {
         "LastName":"Wright",
         "FirstName":"Richard",
         "Gender":"M",
         "FavoriteColor":"Yellow",
         "DateOfBirth":"09/04/1908"
      }
   ]
}
```

## Start Service

To start the `sortd` web server, run:

```text
$ lein ring server
```
or
```text
$ ./sortd-service
```

## Test Coverage

The [cloverage](https://github.com/cloverage/cloverage) test coverage plugin 
has been added to `project.clj`.

To run `cloverage`:

```
$ ./coverage

|---------------------+---------+---------|
|           Namespace | % Forms | % Lines |
|---------------------+---------+---------|
| sortd.api.endpoints |  100.00 |  100.00 |
|  sortd.api.handlers |  100.00 |  100.00 |
|    sortd.logic.core |  100.00 |  100.00 |
|   sortd.logic.parse |  100.00 |  100.00 |
|          sortd.main |  100.00 |  100.00 |
|   sortd.model.store |  100.00 |  100.00 |
|   sortd.routes.home |  100.00 |  100.00 |
|          sortd.util |  100.00 |  100.00 |
|---------------------+---------+---------|
|           ALL FILES |  100.00 |  100.00 |
|---------------------+---------+---------|

```
