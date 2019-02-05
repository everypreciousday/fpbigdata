res = "sample.txt" 
|> File.stream!
|> Flow.from_enumerable
|> Flow.map(fn line -> Poison.decode!(line) end)
|> Flow.partition
|> Flow.filter(fn item -> item["score"] == 50 end)
|> Enum.count
IO.puts res
