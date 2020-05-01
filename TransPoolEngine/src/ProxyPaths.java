import Generated.Paths;

import Generated.Path;

import java.util.ArrayList;
import java.util.List;

public class ProxyPaths {

    protected List<ProxyPath> thePathList=null;

    public ProxyPaths(Paths paths)
    {
        List<Path> pathList=paths.getPath();
        for (Path p: pathList) {
           ProxyPath newPath= new ProxyPath(p);
            thePathList.add(newPath);
        }
    }
    public List<ProxyPath> getPathsList() {
        if (thePathList == null) {
            thePathList = new ArrayList<ProxyPath>();
        }
        return this.thePathList;
    }

}
