defmodule FlowappTest do
  use ExUnit.Case
  doctest Flowapp

  test "greets the world" do
    assert Flowapp.hello() == :world
  end
end
