res = "sample.txt"
|> File.stream!
|> Stream.map(fn line -> Poison.decode!(line) end)
|> Stream.filter(fn item -> item["score"] == 50 end)
|> Enum.count
IO.puts res
