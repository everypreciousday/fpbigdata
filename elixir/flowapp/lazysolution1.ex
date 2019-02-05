data = File.stream!("sample.txt")
map_data = Stream.map(data, fn line -> Poison.decode!(line) end)
filter_data = Stream.filter(map_data, fn item -> item["score"] == 50 end)
res = Enum.count(filter_data)
IO.puts res
