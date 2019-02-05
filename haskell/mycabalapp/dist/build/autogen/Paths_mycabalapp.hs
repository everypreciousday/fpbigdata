module Paths_mycabalapp (
    version,
    getBinDir, getLibDir, getDataDir, getLibexecDir,
    getDataFileName, getSysconfDir
  ) where

import qualified Control.Exception as Exception
import Data.Version (Version(..))
import System.Environment (getEnv)
import Prelude

catchIO :: IO a -> (Exception.IOException -> IO a) -> IO a
catchIO = Exception.catch

version :: Version
version = Version [0,1,0,0] []
bindir, libdir, datadir, libexecdir, sysconfdir :: FilePath

bindir     = "/root/.cabal/bin"
libdir     = "/root/.cabal/lib/x86_64-linux-ghc-7.10.3/mycabalapp-0.1.0.0-4KL6YagXLyqIeNYetlUUaZ"
datadir    = "/root/.cabal/share/x86_64-linux-ghc-7.10.3/mycabalapp-0.1.0.0"
libexecdir = "/root/.cabal/libexec"
sysconfdir = "/root/.cabal/etc"

getBinDir, getLibDir, getDataDir, getLibexecDir, getSysconfDir :: IO FilePath
getBinDir = catchIO (getEnv "mycabalapp_bindir") (\_ -> return bindir)
getLibDir = catchIO (getEnv "mycabalapp_libdir") (\_ -> return libdir)
getDataDir = catchIO (getEnv "mycabalapp_datadir") (\_ -> return datadir)
getLibexecDir = catchIO (getEnv "mycabalapp_libexecdir") (\_ -> return libexecdir)
getSysconfDir = catchIO (getEnv "mycabalapp_sysconfdir") (\_ -> return sysconfdir)

getDataFileName :: FilePath -> IO FilePath
getDataFileName name = do
  dir <- getDataDir
  return (dir ++ "/" ++ name)
