# Zip-Collate #

Pipes standard input into a zipfile.
Splits input into separate files based on a delimiter.

For ease of use, it's best to first make a jar file, like so:

```console
sbt
> compile
> assembly
```

This will create the file `target/scala-2.11/zip_collate-assembly-1.0.jar`.
From there, you can run it as follows:

```console
<<command that produces output>> | java -jar target/scala-2.11/zip_collate-assembly-1.0.jar output.zip
```

...where `output.zip` is the name of the zipfile to produce.
By default, it will split each line of input into a separate file.
Each file will have a name of the form `base_filenameNUMEXTENSION`.

`zip-collate` supports the following options:

- `-b base_filename`: sets what the base name of each file generated will be; this is `base_name` above.  By default, this is `file`
- `-e file_extension`: sets what the file extension of each file will be; this is `EXTENSION` above.  By default this is an empty string.
- `-s start_num`: sets what number we start at; default is `0`
- `-d delimiter`: sets what separates files; default is a newline
- `--print_every times`: will print status information for every `times` files produced; default is no printing


